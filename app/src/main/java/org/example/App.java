package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Arrays;

public class App {

  public static void main(String[] args) throws IOException {
    App app = new App();
    app.run();

  }

  private void run() throws IOException {
    var socket = createSocketExample();
    String message = "GET / HTTP/1.1\r\n" + "Host: example.com\r\n" + "\r\n"; // 요청 끝에 빈 줄 추가

    OutputStream outputStream = socket.getOutputStream();
    outputStream.write(message.getBytes());

    OutputStreamWriter writer = new OutputStreamWriter(outputStream);
    writer.write(message);
    writer.flush();

    System.out.println("Success Request!");

    InputStream inputStream = socket.getInputStream();
    readData(inputStream);
    byte[] bytes = new byte[1_000_000];
    int size = inputStream.read(bytes);

    byte[] data = Arrays.copyOf(bytes, size);
    String readData = new String(data);
    System.out.println(readData);

    socket.close();
  }

  private void readData(InputStream inputStream) throws IOException {
    Reader reader = new InputStreamReader(inputStream);
    CharBuffer charBuffer = CharBuffer.allocate(1_000_000);
    int size = reader.read(charBuffer);

    charBuffer.flip();

    String text = charBuffer.toString();
    System.out.println(text);
  }

  private Socket createSocketExample() throws IOException {
    String host = "example.com";
    int port = 80;
    Socket socket = new Socket(host, port);
    System.out.println("Connect!");
    return socket;
  }
}
