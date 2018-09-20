package motorDeInferencia;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import baseDeConhecimento.Animal;
import baseDeConhecimento.Perguntas;

public class Service {
	private ArrayList<Perguntas> perguntas = new ArrayList<>();
	private ArrayList<Perguntas> perguntasAtivas = new ArrayList<>();
	private ArrayList<Animal> animaisAtivos = new ArrayList<>();

	public void initialize() throws JsonProcessingException, IOException, URISyntaxException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(new File("src/main/java/baseDeConhecimento/base.json"));
		ArrayNode questions = (ArrayNode) root.get("Questions");

		for (int i = 0; i < questions.size(); i++) {
			for (JsonNode selection : questions.get(i)) {

				for (JsonNode gambiarra : selection) {
					Perguntas pergunta = new Perguntas();
					pergunta.setPergunta(gambiarra.asText());
					pergunta.setTier(i);
					perguntas.add(pergunta);
				}
			}
		}
		perguntasAtivas = new ArrayList<>(perguntas);

		ArrayNode animals = (ArrayNode) root.get("Animais");

		for (JsonNode animal : animals) {
			Animal animalList = new Animal();
			animalList.setName(animal.get("Nome").asText());
			animalList.setPersonal(animal.get("Pessoal").asText());
			ArrayList<Integer> properties = new ArrayList<>();
			ArrayNode props = (ArrayNode) animal.get("prop");
			for (int i = 0; i < props.size(); i++) {
				properties.add(props.get(i).asInt());

			}
			animalList.setProps(properties);
			animaisAtivos.add(animalList);

		}

	}

	public Boolean verify(String response, Integer opcao) {
		if (response.toLowerCase().equals("y")) {
			ArrayList<Animal> animaisAuxiliares = new ArrayList<>();
			for (Animal animal : animaisAtivos) {
				for (Integer option : animal.getProps()) {
					if (option == opcao) {
						animaisAuxiliares.add(animal);
					}
				}
			}
			animaisAtivos = animaisAuxiliares;
			return true;

		} else if (response.toLowerCase().equals("n")) {
			ArrayList<Animal> animaisAuxiliares = new ArrayList<Animal>(animaisAtivos);
			for (Animal animal : animaisAtivos) {
				for (Integer option : animal.getProps()) {
					if (option == opcao) {
						animaisAuxiliares.remove(animal);
					}
				}
			}

			animaisAtivos = animaisAuxiliares;
			return false;
		}
		return null;

	}

	public ArrayList<Animal> getAnimals() {
		return animaisAtivos;
	}

	public ArrayList<Perguntas> getQuestions() {
		return null;
	}

	public String getTier(Integer tier) {
		for (Perguntas pergunta : perguntasAtivas) {
			if (pergunta.getTier() == tier) {
				perguntasAtivas.remove(pergunta);
				return pergunta.getPergunta();
			}
		}
		return null;

	}

	public int indexOf(String serviceResponse) {
		for (int i = 0; i < perguntas.size(); i++) {
			if (perguntas.get(i).getPergunta().equals(serviceResponse))
				return i + 1;
		}
		return -1;
	}

	public ArrayList<Perguntas> getPerguntas() {
		return perguntas;
	}

}
