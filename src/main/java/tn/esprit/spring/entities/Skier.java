package tn.esprit.spring.entities;

import java.io.Serializable;
import java.time.LocalDate;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
@Builder
public class Skier implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long numSkier;
	String firstName;
	String lastName;
	LocalDate dateOfBirth;
	String city;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	Subscription subscription;

	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "excursion",
			joinColumns = @JoinColumn(name = "numSkier"),
			inverseJoinColumns = @JoinColumn(name = "numPiste"))
	private Set<Piste> pistes;


	@OneToMany(mappedBy = "skier")
	Set<Registration> registrations;

	public Skier(String firstName, String lastName, LocalDate dateOfBirth, String city,  Set<Piste> pistes, Set<Registration> registrations) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.city = city;

		this.pistes = pistes;
		this.registrations = registrations;
	}

	public Skier(Long numSkier, String firstName, String lastName, LocalDate dateOfBirth, String city, Set<Piste> pistes, Set<Registration> registrations) {
		this.numSkier = numSkier;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.city = city;
		this.pistes = pistes;
		this.registrations = registrations;
	}




}
