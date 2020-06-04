 export class Paper {
    id: number;
    title: string;
   keywords: string;
   topics: string;
   authors: string;
   filename: string;
   description: string;


   constructor(id: number=1, title: string, keywords: string, topics: string, authors: string, filename: string, description: string) {
     this.id = id;
     this.title = title;
     this.keywords = keywords;
     this.topics = topics;
     this.authors = authors;
     this.filename = filename;
     this.description = description;
   }

   contructor() {

   }
 }
