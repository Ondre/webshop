package com.epam.ap.dao;

import com.epam.ap.connection.ConnectionPool;
import com.epam.ap.connection.ConnectionPoolException;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.Assert.assertEquals;

public class ConnectionPoolTest {
    public static final int NUMBER_OF_TASKS = 1000;
    public static final int NUMBER_OF_THREADS = 30;
    public static AtomicInteger completedTasksCount = new AtomicInteger(0);


    @Test
    public void ConnectionPoolShouldCompleteAllTasks() throws InterruptedException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for (int i = 0; i < NUMBER_OF_TASKS; i++) {
            executorService.submit(() -> {
                try (Connection connection = connectionPool.getConnection();
                     Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT * FROM \"PUBLIC\".\"USER\""))
                {
                    completedTasksCount.incrementAndGet();
                    Thread.sleep(100L);
                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                } catch (ConnectionPoolException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        assertEquals(NUMBER_OF_TASKS, completedTasksCount.intValue());
    }


    @Test
    public void test1(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            Connection connection = connectionPool.getConnection();
            connection.close();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(connectionPool.getFreeConnections().size());
        System.out.println(connectionPool.getUsedConnections().size());
    }

}