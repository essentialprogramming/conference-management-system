import { Injectable, Output } from '@angular/core';
import { Paper } from '../models/paper';
import { pTest } from '../models/pTest';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import {map, retry, catchError, tap, mergeMap} from "rxjs/operators";

@Injectable()
export class SpeakerServiceService {

  private paperURL: string;
  private paperUReL = 'http://localhost:8080/api/paper';
  private authorPapers = 'http://localhost:8080/api/author/papers';
  private authorPapersAccepted = 'http://localhost:8080/api/author/acceptedPapers';
  private paper: Paper;
  constructor(private http: HttpClient) {
    this.paperURL = 'http://localhost:8080/api/papers';
  }

  findAll(): Observable<Paper[]> {
    const email = localStorage.getItem('email');
    const url = `${this.authorPapers}/${email}`;
    console.log(url);
    return this.http
    .get<Array<Paper>>(url);
  }

  findAlAccepted(): Observable<Paper[]> {
    const email = localStorage.getItem('email');
    const url = `${this.authorPapersAccepted}/${email}`;
    return this.http
    .get<Array<Paper>>(url);
  }

  getPaper(id: number): Observable<Paper> {
    const url = `${this.paperUReL}/${id}`;
    return this.http.get<Paper>(url);
  }

  save(paper): Observable<Paper>
    {
      return this.http.post<Paper>(this.paperUReL,paper);
  }


  update(paper, id) : Observable<Paper> {
    const url = `${this.paperUReL}/${id}`;
    return this.http.put<Paper>(url,paper);
  }

  postFile(fileToUpload, id): Observable<void> {
    const endpoint = 'http://localhost:8080/api/upload';
    const url = `${endpoint}/${id}`;

    const formData: FormData = new FormData();

    formData.append('file', fileToUpload);
    formData.append('file', fileToUpload.name);

    const myHeadr = new HttpHeaders();
    myHeadr.append('Content-Type', 'multipart/form-data');
    myHeadr.append('Accept', 'application/json');
    return this.http
      .post(url, formData, { headers: myHeadr })
      .map(() => { res => res.json()});


  }

  getFile(id: number){
    const endpoint = 'http://localhost:8080/api/files';
    const url = `${endpoint}/${id}`;
    return this.http.get(url);
  }

}
