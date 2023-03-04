import { Component, OnInit } from '@angular/core';
import { ApplicationService } from './main.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  listaDeTarjetas:any[] = [];
  constructor(private service: ApplicationService){

  }
  ngOnInit(): void {
    this.service.optenerTarjetas().subscribe({
      next:(value:any)=>{ 
        this.listaDeTarjetas = value.data;
      }
    })
  }
}
