package it.epicode.be.epicenergyservices.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@Entity
@Table(name="municipalities")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Municipality {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    @Column(name = "district_code")
	    private int districtCode;
	    private String name;
	    @ManyToOne
	    @JsonBackReference
	    private Province province;
}
