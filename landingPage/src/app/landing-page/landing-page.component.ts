import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }
  onGoToPage(){

    let routeUrl = '/login';
    const url = this.router.serializeUrl(this.router.createUrlTree([routeUrl]));
    window.open("http://www.tharwh.com/sheepFarm/", '_self');
  }

}
