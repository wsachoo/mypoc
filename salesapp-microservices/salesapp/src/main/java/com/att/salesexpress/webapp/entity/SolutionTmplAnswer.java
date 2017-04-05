package com.att.salesexpress.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SALES_SOL_TMPL_ANS database table.
 * 
 */
@Entity
@Table(name="SALES_SOL_TMPL_ANS")
@NamedQuery(name="SolutionTmplAnswer.findAll", query="SELECT s FROM SolutionTmplAnswer s")
public class SolutionTmplAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="ANS_DESC")
	private String ansDesc;

	@Column(name="ANS_SEQ_ID")
	private BigDecimal ansSeqId;

	//bi-directional many-to-one association to SolutionTmplQuestion
	@ManyToOne
	@JoinColumn(name="QUES_ID")
	private SolutionTmplQuestion salesSolTmplQue;

	public SolutionTmplAnswer() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAnsDesc() {
		return this.ansDesc;
	}

	public void setAnsDesc(String ansDesc) {
		this.ansDesc = ansDesc;
	}

	public BigDecimal getAnsSeqId() {
		return this.ansSeqId;
	}

	public void setAnsSeqId(BigDecimal ansSeqId) {
		this.ansSeqId = ansSeqId;
	}

	public SolutionTmplQuestion getSalesSolTmplQue() {
		return this.salesSolTmplQue;
	}

	public void setSalesSolTmplQue(SolutionTmplQuestion salesSolTmplQue) {
		this.salesSolTmplQue = salesSolTmplQue;
	}

}