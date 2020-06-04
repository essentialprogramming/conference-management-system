export class Program{
  id: string;
  date: string;
  interval: string;
  abstractDeadline: string;
  proposalDeadline: string;
  biddingDeadline: string;

  constructor(id: string, date: string, interval: string, abstractDeadline: string, proposalDeadline: string, biddingDeadline: string) {
    this.id = id;
    this.date = date;
    this.interval = interval;
    this.abstractDeadline = abstractDeadline;
    this.proposalDeadline = proposalDeadline;
    this.biddingDeadline = biddingDeadline;
  }
}
