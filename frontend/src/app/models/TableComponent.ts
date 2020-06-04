export class TableComponent {

    public currentCompany;
  
    public selectCompany(event: any, item: any) {
  
      this.currentCompany = item.name;
    }
  
  }