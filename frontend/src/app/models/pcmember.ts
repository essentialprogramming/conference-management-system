export class PcMember {
  id: string;
  status: string;
  email: string;

  constructor(id: string, status: string, email: string) {
    this.id = id;
    this.status = status;
    this.email = email;
  }
}
