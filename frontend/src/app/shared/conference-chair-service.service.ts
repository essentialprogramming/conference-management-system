import { Injectable } from '@angular/core';
import {Paper} from '../models/Paper';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Conference} from '../models/Conference';
import {PcMember} from '../models/pcmember';
import {ConferenceTest} from '../models/ConferenceTest';
import {SectionTest} from '../models/SectionTest';
import {PcMem} from '../models/PcMem';

@Injectable()
export class ConferenceChairServiceService {
  private conferenceURL= 'http://localhost:8080/api/events';
  private paperURL = 'http://localhost:8080/api/papers';
  private  paperUReL = 'http://localhost:8080/api/paper';
  private conferenceUReL = 'http://localhost:8080/api/event';
  private updateConferenceURL = 'http://localhost:8080/api/event/update';
  private pcmembersUReL = 'http://localhost:8080/api/programCommittee/members';
  private sectionURL = 'http://localhost:8080/api/event/section';
  private assignSupervisorSectionURL = 'http://localhost:8080/api/event/section/supervisor';
  private assignPaperURL = 'http://localhost:8080/api/programCommittee/assign/paper/to/review';
  private assignPaperToSectionURL = 'http://localhost:8080/api/programCommittee/assign/paper/to/section';
  private assignReviewerToEventURL = 'http://localhost:8080/api/event/committee';
  private unnassignedPcMembersURL='http://localhost:8080/api/programCommittee/unassignedPCMembers';
  private conference: Conference;
  constructor(private http: HttpClient) {
    this.conferenceURL = 'http://localhost:8080/api/events';
  }

  findAll(): Observable<Conference[]> {
    return this.http
      .get<Array<Conference>>(this.conferenceURL);
  }

  getConference(id: number): Observable<Conference> {
    const url = `${this.conferenceUReL}/${id}`;
    return this.http.get<Conference>(url);
  }
  findAllPcMembers(): Observable<PcMember[]> {
    return this.http
      .get<Array<PcMember>>(this.pcmembersUReL);
  }

  unassignedPcMembers():Observable<PcMember[]>{
    const url=`${this.unnassignedPcMembersURL}`;
    return this.http.get<Array<PcMember>>(url);
  }

  findPcMembersForPaper(id): Observable<PcMember[]> {
    const url = `${this.pcmembersUReL}/${id}`;
    return this.http
      .get<Array<PcMember>>(url);
  }


  findAllPapers(): Observable<Paper[]> {
    return this.http
      .get<Array<Paper>>(this.paperURL);
  }


  updateConference(id,conference): Observable<Conference> {

    const url = `${this.updateConferenceURL}/${id}`;
    return this.http.put<Conference>(url, conference);
  }
  saveSection(id: string, section): Observable<SectionTest> {
    const url = `${this.sectionURL}/${id}`;
    return this.http.post<SectionTest>(url, section);
  }
  assignPaper(paperId: string, email: string, paper) {
    const url = `${this.assignPaperURL}/${paperId}/${email}`;
    return this.http.put(url, paper);
  }
  assignSupervisortoSection(sectionId, email: string, section){
    const url = `${this.assignSupervisorSectionURL}/${sectionId}/${email}`;
    return this.http.put(url, section);
  }
  assignPapertoSection(paperId,sectionId,section){
    const url = `${this.assignPaperToSectionURL}/${paperId}/${sectionId}`;
    return this.http.put(url, section);


  }
  assignReviewerToEvent(event,eventId,email){
    const url = `${this.assignReviewerToEventURL}/${eventId}/${email}`;
    return this.http.put(url, event);


  }

}
