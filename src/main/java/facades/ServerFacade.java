/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Post;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sinanjasar
 */
public class ServerFacade {

    private static ServerFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private ServerFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static ServerFacade getServerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ServerFacade();
        }
        return instance;
    }

    /**
     *
     * @param resource, what to fetch from the server
     * @return a json representation of what is returned by the server
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    private static String fetchFromServer(String server) throws MalformedURLException, ProtocolException, IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts/" + server);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        String jsonStr = "";
        try (Scanner scan = new Scanner(con.getInputStream())) {
            while (scan.hasNext()) {
                jsonStr += scan.nextLine();
            }
        }
        return jsonStr;
    }

    /**
     * Should call fetchFromServer method on multiple servers
     * 
     * @param servers
     * @return A list containing all json objects returned by servers.
     */
    public List<Post> fetchFromServers(String[] servers) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Queue<Future> futureList = new LinkedList();
        List<Post> posts = new ArrayList();
        for (String s : servers) {
            Future<Post> future = executor.submit(() -> {
                JsonObject jsonObj = new JsonParser().parse(fetchFromServer(s))
                        .getAsJsonObject();
                return new Post(jsonObj.get("id").getAsInt(),
                        jsonObj.get("title").getAsString(), jsonObj.get("body").getAsString());
            });
            futureList.add(future);
        }
        while (!futureList.isEmpty()) {
            Future<Post> f = futureList.poll();
            if (f.isDone()) {
                try {
                    posts.add(f.get());
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                futureList.add(f);
            }
        }
        return posts;
    }
}
