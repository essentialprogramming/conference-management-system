export class pTest {
    title: string;
   keywords: string[];
   topics: string[];
   authors: string[];
   fileName: string;
   description: string;


   constructor( title: string, keywords: string[], topics: string[], authors: string[], filename: string, description: string) {
     this.title = title;
     this.keywords = keywords;
     this.topics = topics;
     this.authors = authors;
     this.fileName = filename;
     this.description = description;
   }
}
