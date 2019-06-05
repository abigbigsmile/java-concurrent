package com.smile.thread.util;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread implements Runnable{

    private Socket client;
    private InputStream ins;
    private OutputStream ops;
    private static HashMap<String, String> map = new HashMap<>();

    static {
        map.put("html", "text/html;charset=utf-8");
        map.put("jpg", "image/jpeg");
    }

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        response();
    }

    public void response(){
        try {

            ins = client.getInputStream();
            ops = client.getOutputStream();
            PrintWriter pw = new PrintWriter(ops);

            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String line = br.readLine().split(" ")[1].replace("/", "\\");
            if(line.equals("\\")){
                line += "hello.html";
            }
            System.out.println(line);

            InputStream its = new FileInputStream("C:\\Users\\Smile\\Desktop\\WebRoot" + line);

            BufferedReader bfr = new BufferedReader(new InputStreamReader(its));
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type: " + map.get(line.substring(line.lastIndexOf(".")+1)));
            pw.println("Content-Length: " + its.available());
            pw.println("");
            pw.flush();

//            String l = null;
//            while((l = bfr.readLine()) != null){
//                pw.println(l);
//            }

            //使用二进制传图片
            byte[] b = new byte[1024];
            int len = 0;
            while((len = its.read(b)) != -1){
                ops.write(b, 0, len);
            }


            pw.flush();
            pw.close();
//            br.close();
            its.close();
            bfr.close();
            ins.close();
            ops.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
