import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { exposedApi } from 'src/env/environment';
@Injectable({
  providedIn: 'root'
})
export class ApplicationService {
    url = exposedApi.API + "/api/"
  constructor(private http: HttpClient) {}

  optenerTarjetas(){
    return this.http.get(`${this.url}tarjetas`);
  }
}
