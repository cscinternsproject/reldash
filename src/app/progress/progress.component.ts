import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';
import {BehaviorSubject} from 'rxjs';

@Component({
  selector: 'app-progress',
  templateUrl: './progress.component.html',
  styleUrls: ['./progress.component.css']
})
export class ProgressComponent implements OnInit {

 Bar : any;

  constructor(private dataservice: ServiceService) { }

  ngOnInit(): void {

    this.dataservice.currentMessage.subscribe(message => {

      this.Bar = message;
      console.log(this.Bar)
    });



  }

}
