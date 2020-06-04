export class ProgramUpdate{
  date: string;
  interval: string;
  abstractDeadline: string;
  proposalDeadline: string;
  biddingDeadline: string;

  constructor(date: string, interval: string, abstractDeadline: string, proposalDeadline: string, biddingDeadline: string) {
    this.date = date;
    this.interval = interval;
    this.abstractDeadline = abstractDeadline;
    this.proposalDeadline = proposalDeadline;
    this.biddingDeadline = biddingDeadline;
  }
}
