package com.psm.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/webSocket/{orderId}") // make the class as 服务节点
public class WebSocketServer {

    // use ConcurrentHashMap because of many people send the request at a time.
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();


    @OnOpen //@OnOpen runs when front end request to build link
    public void open(@PathParam("orderId") String orderId, Session session){
        System.out.println(".... websocket request....."+ orderId);
        sessionMap.put(orderId, session);
    }

    @OnClose // @OnClose runs when front end close the page or close websocket link.
    public void open(@PathParam("orderId") String orderId){
        sessionMap.remove(orderId);
    }

    public static void sendMsg(String orderId, String msg){
        try {
            Session session = sessionMap.get(orderId);
            session.getBasicRemote().sendText(msg);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
