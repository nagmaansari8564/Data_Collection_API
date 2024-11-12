package in.ashokit.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "DC_CHILDREN")
public class DcChildren {

	@Id
	@GeneratedValue
	private Integer childrenId;
	private Long caseNum;
	private LocalDate childDob;
	private Long childSsn;
	

}
