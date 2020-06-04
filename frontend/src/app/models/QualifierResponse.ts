export class QualifierResponse {
  qualifier: string;
  recommendation: string;
  paperId: string;
  reviewer: string;

  constructor(qualifier: string, recommendation: string, paperId: string, reviewer: string) {
    this.qualifier = qualifier;
    this.recommendation = recommendation;
    this.paperId = paperId;
    this.reviewer = reviewer;
  }
}
