import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import {Paper} from '../../models/Paper';
import { SpeakerServiceService } from '../../shared/speaker-service.service';
import { switchMap } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { pTest } from '../../models/pTest';

@Component({
  selector: 'app-proposal-form',
  templateUrl: './proposal-form.component.html',
  styleUrls: ['./proposal-form.component.css']
})
export class ProposalFormComponent implements OnInit{
  fileToUpload: File = null;
  proposal: Paper;
  flag: boolean;
  private stoking;
  constructor(private route: ActivatedRoute,
              private router: Router,
              private speakerService: SpeakerServiceService
  ) {

    this.proposal = new Paper( null, null, null, null, null, null, null);
//     var str = new String("Apple--Basket--Share--Options")
// var splits = str.split("--")
// console.log(splits)
  }

  onSubmit() {
    console.log("a intrat aici");
      var str1 = this.proposal.authors;
      var splits = str1.split(",");
      var str2 = this.proposal.topics;
      var splits2 = str2.split(",");
      var str3 = this.proposal.keywords;
      var splits3 = str3.split(",");
    //console.log(this,this.proposal);
    let x= new pTest(this.proposal.title,splits3, splits2, splits, this.proposal.filename, this.proposal.description);
    this.speakerService.save(
    x
  )
  .subscribe(res => {
    this.router.navigate(['/submitProposal', res['id']]).then(() => {
      window.location.reload();
  });
    //console.log(this.stoking);
  });

  }
  ngOnInit(): void {

    if (this.route.snapshot.params['id']){
      this.flag = true;
      console.log('id: ', this.route.snapshot.params['id']);
      this.route.params
      .pipe(switchMap((params: Params) => this.speakerService.getPaper(+params['id'])))
      .subscribe(paperr => this.proposal = paperr);

    }
    else{
    //this.proposal = new Paper(1,'salut buna', 'aasd',['a','dsadasda'],['a','asdasddasd'],'a','a')
    this.proposal = new Paper( null,null,null,null,localStorage.getItem('email'),null,null);
    console.log("teapa");
    }
  }

  onFileSelected(event) {
    if(event.target.files.length > 0)
     {
       //console.log(event.target.files[0].name);
       this.proposal.filename=event.target.files[0].name;
       console.log(this.proposal.filename);
     }
   }
  EditNow(){
    var thisID;
    var str1 = this.proposal.authors.toString();
    console.log(str1);
      var splits = str1.split(",");
      var str2 = this.proposal.topics.toString();
      var splits2 = str2.split(",");
      var str3 = this.proposal.keywords.toString();
      var splits3 = str3.split(",");
    console.log(this.proposal.filename);
    let x= new pTest(this.proposal.title,splits3, splits2, splits, this.proposal.filename, this.proposal.description);

    this.speakerService.update(
      x, this.route.snapshot.params['id']
    )
    .subscribe(res => {
      this.router.navigate(['/mysubmissions']).then(() => {
        window.location.reload();
    });
      //console.log(this.stoking);
    });
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
    this.proposal.filename=this.fileToUpload.name;
  }


  newFunciton(){
    this.speakerService.postFile(this.fileToUpload, this.route.snapshot.params['id']).subscribe(data => {
      // do something, if upload success
      console.log("it worked");
      });
  }

  downloadFile(){
    this.speakerService.getFile(this.route.snapshot.params['id']).subscribe(data => this.downloadFilee(data)),//console.log(data),
    error => console.log('Error downloading the file.'),
    () => console.info('OK');

  }

  downloadFilee(data: any) {
    const blob = new Blob([data], { type: 'text/csv' });
    const url= window.URL.createObjectURL(blob);
    window.open(url);
  }



}
