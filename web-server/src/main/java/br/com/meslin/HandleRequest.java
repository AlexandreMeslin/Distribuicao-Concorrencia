package br.com.meslin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class HandleRequest implements Runnable {
    private Socket clientSocket;
    private String path;
    
    /**
     * Constructor
     */
    public HandleRequest(Socket clientSocket, String path) {
        this.clientSocket = clientSocket;
        this.path = path;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream();
             PrintWriter writer = new PrintWriter(out, true)) {

            // Ler a primeira linha da requisição HTTP (a linha de solicitação)
            String requestLine = in.readLine();
            if(requestLine == null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar a conexão com o cliente: " + e.getMessage());
                }
                return;
            }
            String[] palavras = requestLine.split(" ");
            if(palavras[1].equals("/")) palavras[1] = "/index.html";
            String filename = path + palavras[1];
            System.out.println("Requisição recebida: " + requestLine + " do arquivo " + filename);

            String linha = null;
            // Ignorar as outras linhas do cabeçalho HTTP (podemos fazer leitura até encontrar uma linha vazia)
            while ((linha = in.readLine()) != null && linha.length() != 0) {
                // Apenas avançando o ponteiro
            }

            // Enviar uma resposta simples ao cliente
            String response = "HTTP/1.1 200 OK\r\n" +
                              "Content-Type: text/html\r\n" +
                              "Connection: close\r\n" +
                              "\r\n";
            writer.print(response);
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                while ((response = br.readLine()) != null) {
                    writer.print(response);
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao tratar a requisição: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar a conexão com o cliente: " + e.getMessage());
            }
        }
    }
}
