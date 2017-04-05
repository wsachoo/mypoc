package com.att.salesexpress.webapp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the SALES_SOL_TMPL_QUES database table.
 * 
 */
@Entity
@Table(name = "SALES_SOL_TMPL_QUES")
@NamedQuery(name = "SolutionTmplQuestion.findAll", query = "SELECT s FROM SolutionTmplQuestion s")
public class SolutionTmplQuestion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "QUES_COL_NAME")
	private String quesColName;

	@Column(name = "QUES_DESC")
	private String quesDesc;

	@Column(name = "QUES_SEQ_ID")
	private BigDecimal quesSeqId;

	@Column(name = "QUES_TYPE")
	private String quesType;

	// bi-directional many-to-one association to SolutionTmplAnswer
	@OneToMany(mappedBy = "salesSolTmplQue")
	private List<SolutionTmplAnswer> salesSolTmplAns;

	public SolutionTmplQuestion() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuesColName() {
		return this.quesColName;
	}

	public void setQuesColName(String quesColName) {
		this.quesColName = quesColName;
	}

	public String getQuesDesc() {
		return this.quesDesc;
	}

	public void setQuesDesc(String quesDesc) {
		this.quesDesc = quesDesc;
	}

	public BigDecimal getQuesSeqId() {
		return this.quesSeqId;
	}

	public void setQuesSeqId(BigDecimal quesSeqId) {
		this.quesSeqId = quesSeqId;
	}

	public String getQuesType() {
		return this.quesType;
	}

	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}

	public List<SolutionTmplAnswer> getSalesSolTmplAns() {
		return this.salesSolTmplAns;
	}

	public void setSalesSolTmplAns(List<SolutionTmplAnswer> salesSolTmplAns) {
		this.salesSolTmplAns = salesSolTmplAns;
	}

	public SolutionTmplAnswer addSalesSolTmplAn(SolutionTmplAnswer salesSolTmplAn) {
		getSalesSolTmplAns().add(salesSolTmplAn);
		salesSolTmplAn.setSalesSolTmplQue(this);

		return salesSolTmplAn;
	}

	public SolutionTmplAnswer removeSalesSolTmplAn(SolutionTmplAnswer salesSolTmplAn) {
		getSalesSolTmplAns().remove(salesSolTmplAn);
		salesSolTmplAn.setSalesSolTmplQue(null);

		return salesSolTmplAn;
	}

	@Override
	public String toString() {
		return "SolutionTmplQuestion [id=" + id + ", quesColName=" + quesColName + ", quesDesc=" + quesDesc
				+ ", quesSeqId=" + quesSeqId + ", quesType=" + quesType + ", salesSolTmplAns=" + salesSolTmplAns + "]";
	}

}