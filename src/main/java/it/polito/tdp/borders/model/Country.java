package it.polito.tdp.borders.model;

public class Country {
private int id;
private String abbr;
private String nome;
public Country(int id, String abbr, String nome) {
	super();
	this.id = id;
	this.abbr = abbr;
	this.nome = nome;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getAbbr() {
	return abbr;
}
public void setAbbr(String abbr) {
	this.abbr = abbr;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
@Override
public String toString() {
	return   nome ;
}
}
