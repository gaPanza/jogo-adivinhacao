package baseDeConhecimento;

import java.util.ArrayList;

public class Animal {
	private String name;
	private String personal;
	private ArrayList<Integer> props;

	public void setProps(ArrayList<Integer> props) {
		this.props = props;
	}

	public ArrayList<Integer> getProps() {
		return props;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

}
