import { Injectable } from '@angular/core';
import {Paper} from '../models/paper';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Qualifier} from '../models/Qualifier';
import {QualifierResponse} from '../models/QualifierResponse';

@Injectable()
export class ReviewerServiceService {
  private paperURL: string;
  private paperUReL = 'http://localhost:8080/api/paper';
  private bidUrl = 'http://localhost:8080/api/programCommittee/bid';
  private qualifierUrl = 'http://localhost:8080/api/programCommittee/review';
  private acceptedPapersURL = 'http://localhost:8080/api/programCommittee/papers';
  private paper: Paper;
  private qualifier: Qualifier;
  constructor(private http: HttpClient) {
    this.paperURL = 'http://localhost:8080/api/papers';
  }

  findAll(): Observable<Paper[]> {
    return this.http
      .get<Array<Paper>>(this.paperURL);
  }

  getPaper(id: number): Observable<Paper> {
    const url = `${this.paperUReL}/${id}`;
    return this.http.get<Paper>(url);
  }
  getAcceptedPapers(email): Observable<Paper[]> {
    const url = `${this.acceptedPapersURL}/${email}`;
    return this.http.get<Array<Paper>>(url);
  }
  bid(id: string, status: string ): Observable<Paper> {
    const email = localStorage.getItem('email');
    const url = `${this.bidUrl}/${id}/${email}/${status}`;
    return this.http.get<Paper>(url);
  }
  qualifierR(id: string, qualifier): Observable<QualifierResponse> {
    const email = localStorage.getItem('email');
    const url = `${this.qualifierUrl}/${id}/${email}`;
    return this.http.put<QualifierResponse>(url, qualifier);
  }
}
