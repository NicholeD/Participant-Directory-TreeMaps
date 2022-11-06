package com.kenzie.futures.musicaccountreceiver.futures;

import com.kenzie.futures.musicaccountreceiver.futures.resources.AmazonMusicAccount;
import com.kenzie.futures.musicaccountreceiver.futures.resources.ImportAccountTask;
import com.kenzie.futures.musicaccountreceiver.futures.resources.MusicAccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MusicAccountRetriever {

    private MusicAccountService accountService;

    /**
     * Constructor for MusicAccountRetriever.
     */
    public MusicAccountRetriever() {
        accountService = new MusicAccountService();
    }

    /**
     * Retrieves a list of AmazonMusicAccounts given a list of String account IDs.
     * PARTICIPANTS: Complete this method. It should submit an ImportAccountTask to the ExecutorService and return the
     *   Future&lt;AmazonMusicAccount&gt;.
     * @param accountIDs List of String account IDs.
     * @return List of imported AmazonMusicAccounts.
     */
    public List<AmazonMusicAccount> retrieveAccounts(List<String> accountIDs) throws InterruptedException {
        ExecutorService accountExecutor = Executors.newCachedThreadPool();
        List<AmazonMusicAccount> accountList = new ArrayList<>();

        List<Future<AmazonMusicAccount>> results = new ArrayList<>();
        try {
            results = accountExecutor.invokeAll(generateImportTasks(accountIDs));
        } catch (InterruptedException e) {
            System.out.println("MusicAccountStatsManager was interrupted.");
            e.printStackTrace();
        }

        for (Future<AmazonMusicAccount> result : results) {
            try {
                AmazonMusicAccount musicAccount = new AmazonMusicAccount(result.get().getAccountName(),
                        result.get().getNumOfPlaylists(),
                        result.get().getTopGenre());

                accountList.add(musicAccount);
            } catch (ExecutionException e) {
                System.out.println("ImportAccountTask threw an exception.");
                e.printStackTrace();
            }
        }

        accountExecutor.shutdown();

        return accountList;
    }
    
    private List<ImportAccountTask> generateImportTasks(List<String> accountIDs) {
        List<ImportAccountTask> tasks = new ArrayList<>();

        for (String id : accountIDs) {
            tasks.add(new ImportAccountTask(accountService, id));
        }

        return tasks;
    }
}
