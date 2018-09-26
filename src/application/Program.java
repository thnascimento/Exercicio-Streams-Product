package application;

import entities.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
*Lê todos os protudos de um arquivo, em seguida imprime o valor da média de todos e
*uma lista com os nomes dos produtos que tem o preço menor que a média
*em ordem alfabética inversa
*/

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the full file path: ");
        String path = sc.nextLine();

        try(BufferedReader br = new BufferedReader(new FileReader(path))){

            List<Product> list = new ArrayList<>();

            String line = br.readLine();
            while(line != null){
                String[] fields = line.split(",");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));

                line = br.readLine();
            }

            Double avg = list.stream().map(x -> x.getPrice())
                    .reduce(0.0, (x, y) -> x + y ) / list.size();

            System.out.println("Average price: " + String.format("%.2f", avg));

            Comparator<String> comp = (x,y) -> x.toUpperCase().compareTo(y.toUpperCase());

            List<String> names = list.stream()
                    .filter(x -> x.getPrice() < avg)
                    .map(x -> x.getName())
                    .sorted(comp.reversed())
                    .collect(Collectors.toList());

            names.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
