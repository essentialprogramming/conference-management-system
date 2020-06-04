import { Component, OnInit } from '@angular/core';
import {Section} from '../../models/section';
import {pTest} from '../../models/pTest';
import {ConferenceTest} from '../../models/ConferenceTest';
import {SectionTest} from '../../models/SectionTest';
import {ConferenceChairServiceService} from '../../Shared/conference-chair-service.service';

@Component({
  selector: 'app-section',
  templateUrl: './section.component.html',
  styleUrls: ['./section.component.css']
})
export class SectionComponent implements OnInit {
  section: SectionTest;
  constructor(private service: ConferenceChairServiceService) {
    this.section = new SectionTest(null);
  }

  ngOnInit() {
  }

  onSubmit() {
    console.log(this.section.name);
   // const x = new SectionTest(this.section.name);
    this.service.saveSection('1', this.section).subscribe(res => console.log(res));

  }

}
