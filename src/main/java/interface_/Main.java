package interface_;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import com.fasterxml.jackson.core.JsonProcessingException;
import baseDeConhecimento.Animal;
import motorDeInferencia.Service;

public class Main {
	static Service service = new Service();
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args)
			throws InterruptedException, JsonProcessingException, IOException, URISyntaxException {

		service.initialize();

		// Carrega e mapeia os animais

		System.out.println("--- Olá Bem Vindo ao advinhador de animais ---");
		System.out.println("----------------------------------------------");
		System.out.println("------------ PENSE EM UM ANIMAL --------------");
		System.out.println("----------------------------------------------");

		/*
		 * Thread.currentThread(); Thread.sleep(5000);
		 */
		int j = 1;
		int size = service.getPerguntas().size();
		for (int i = 0; i < size; i++) {
			int sizeAnimals = service.getAnimals().size();
			if (sizeAnimals == 1) {
				System.out.println("Seu animal é: " + service.getAnimals().get(0).getName());
				System.exit(0);
			} else if (sizeAnimals <= 4) {
				for (Animal animal : service.getAnimals()) {
					if (ask(animal.getPersonal())) {
						System.out.println("Seu animal é: " + animal.getName());
						System.exit(0);
					}
				}
			}
			String serviceResponse = service.getTier(i);
			j = service.indexOf(serviceResponse);

			if (serviceResponse == null)
				continue;

			if (ask(serviceResponse, j)) {

			} else {
				i = i - 1;
			}
			j++;
		}

	}

	public static Boolean ask(String serviceResponse, int j) {
		System.out.println(serviceResponse);
		Boolean b = service.verify(scanner.nextLine(), j);
		if (b == null) {
			return ask(serviceResponse, j);
		}
		return b;

	}

	public static Boolean ask(String animalPersonal) {
		System.out.println(animalPersonal);
		String response = scanner.nextLine();
		if (response.equals("y"))
			return true;
		else if (response.equals("n"))
			return false;
		else
			return ask(animalPersonal);

	}

}
