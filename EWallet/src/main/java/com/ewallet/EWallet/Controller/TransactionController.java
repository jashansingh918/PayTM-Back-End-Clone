package com.ewallet.EWallet.Controller;

import com.ewallet.EWallet.Model.AddBalanceDetails;
import com.ewallet.EWallet.Model.Transaction;
import com.ewallet.EWallet.Model.User;
import com.ewallet.EWallet.Model.Wallet;
import com.ewallet.EWallet.Repository.TransactionRepository;
import com.ewallet.EWallet.Repository.WalletRepository;
import com.ewallet.EWallet.ThirdPartyServices.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    //sendMoney
    @PostMapping("/sendMoney")
    public Transaction sendMoney(@RequestBody Transaction transaction) throws Exception{
        //from the request transaction body, we will get sender id
        //and receiver id and corresponding users by using our another user service running
        //on different spring boot application
        //and update their wallets
        User sender = userService.findUserById(transaction.getSender_id());
        User receiver = userService.findUserById(transaction.getReceiver_id());
        if(sender ==null || receiver == null){
            LOGGER.error("transaction cannot be completed since one of sender/receiver does not exist for request : {}", transaction.toString());
            throw new Exception("Bad Payload");
        }
        Wallet senderWallet = walletRepository.findByUserId(sender.getId());
        Wallet receiverWallet = walletRepository.findByUserId(receiver.getId());

        int amount = transaction.getAmount();

        if(senderWallet.getBalance() < amount){
            LOGGER.error("Not Sufficient Balance this transaction {}", transaction.toString());
            throw new Exception("Not sufficient balance");
        }

        senderWallet.setBalance(senderWallet.getBalance()-amount);
        receiverWallet.setBalance(receiverWallet.getBalance()+amount);
        //false- no transaction, true- successful transaction
        transaction.setStatus(Boolean.TRUE);
        LOGGER.info("transaction was successful with sender {} and receiver {}",sender.toString(), receiver.toString());
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);
        return transactionRepository.save(transaction);

    }

    //getBalance


    //addBalance
    //request ---- user id, amount
    //wallet already have 100 rs, add 10
    @PutMapping("/addBalance")  //put is used to replace something which already exist and post is used to create a new row
    public Integer addBalance(@RequestBody AddBalanceDetails addBalanceDetails){
        LOGGER.info("Got this request for adding balance : {}", addBalanceDetails.toString());
        //find wallet by user id
        Wallet wallet = walletRepository.findByUserId(addBalanceDetails.getUserid());
        //set balance in the wallet
        wallet.setBalance(addBalanceDetails.getAmount()+ wallet.getBalance());
        walletRepository.save(wallet);
        return wallet.getBalance();
    }

    @GetMapping("/getBalance/{userid}")
    public Integer getBalance(@PathVariable int userid) throws Exception{
        Wallet wallet = walletRepository.findByUserId(userid);
        if(wallet == null){
            throw new Exception("Wallet is not created for this user : "+userid);
        }
        return wallet.getBalance();
    }

}
