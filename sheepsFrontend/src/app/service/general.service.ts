import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { Lamb } from '../vo/Lamb';
import { Sheep } from '../vo/Sheep';
import jwt_decode from 'jwt-decode';
import { TokenDecoded } from '../vo/TokenDecoded';
@Injectable({
  providedIn: 'root'
})
export class GeneralService {

  decoded: TokenDecoded;

  constructor(private httpClient: HttpClient,private router: Router) { }

    addSheep(body: any) {
      const url = environment.BaseUrl + '/addsheep';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.post(url, JSON.stringify(body), { headers: header });
    }

    addLamb(body: any) {
      const url = environment.BaseUrl + '/lambs/addlamb';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.post(url, JSON.stringify(body), { headers: header });
    }

    saveFeed(body: any) {
      const url = environment.BaseUrl + '/feed/savefeed';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.post(url, JSON.stringify(body), { headers: header });
    }

    saveMedicine(body: any) {
      const url = environment.BaseUrl + '/medicine/savemedicine';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.post(url, JSON.stringify(body), { headers: header });
    }

    saveFeedLookups(body: any) {
      const url = environment.BaseUrl + '/feedlookups/savelookups';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.post(url, JSON.stringify(body), { headers: header });
    }

    savePlace(body: any) {
      const url = environment.BaseUrl + '/places/saveplace';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.post(url, JSON.stringify(body), { headers: header });
    }
    saveMovements(body: any) {
      const url = environment.BaseUrl + '/movement/savemovements';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.post(url, JSON.stringify(body), { headers: header });
    }


    savePlacesFeed(body: any) {
      const url = environment.BaseUrl + '/places/saveplacesfeed';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.post(url, JSON.stringify(body), { headers: header });
    }
    login(body: any) {
      const url = environment.BaseUrl + '/login';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.post(url, JSON.stringify(body), { headers: header });
    }

    saveMedicineSheep(body: any) {
      const url = environment.BaseUrl + '/medicine/savemedicinesheep';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.post(url, JSON.stringify(body), { headers: header });
    }
    getSheeps(body: any) {
      const url = environment.BaseUrl + '/getsheeps';
      const header = {'Content-Type': 'application/json; charset=utf-8'};
      return this.httpClient.post(url, JSON.stringify(body), {headers: header});
    }

    getAllSheeps() {
      const url = environment.BaseUrl + '/getallsheeps';
      return this.httpClient.get(url);
    }

    getSheepForEdit(body: any) {
      const url = environment.BaseUrl + '/getsheepbydata';
      const header = {'Content-Type': 'application/json; charset=utf-8'};
      return this.httpClient.post<Sheep>(url, JSON.stringify(body), {headers: header});
    }

    getAllValidMedicine(){
      const url = environment.BaseUrl + '/medicine/getallvalidmedicine';
      return this.httpClient.get(url);

    }

    getLambForEdit(body: any) {
      const url = environment.BaseUrl + '/lambs/getlambbydata';
      const header = {'Content-Type': 'application/json; charset=utf-8'};
      return this.httpClient.post<Lamb>(url, JSON.stringify(body), {headers: header});
    }

    getLambs(body: any) {
      const url = environment.BaseUrl + '/lambs/getlambs';
      const header = {'Content-Type': 'application/json; charset=utf-8'};
      return this.httpClient.post(url, JSON.stringify(body), {headers: header});
    }

    getSheepMovements(body :any){
      const url = environment.BaseUrl + '/movement/getsheepmovements';
      const header = {'Content-Type': 'application/json; charset=utf-8'};
      return this.httpClient.post(url, JSON.stringify(body), {headers: header});
    }

    getMedicines() {
      const url = environment.BaseUrl + '/medicine/getmedicines';
      return this.httpClient.get(url);
    }

    getEwes() {
      const url = environment.BaseUrl + '/getewes';
      return this.httpClient.get(url);
    }

    getFeed() {
      const url = environment.BaseUrl + '/feed/getallfeed';
      return this.httpClient.get(url);
    }


    getStoredFeed() {
      const url = environment.BaseUrl + '/feed/getallstoredfeed';
      return this.httpClient.get(url);
    }

    getFeedLookups() {
      const url = environment.BaseUrl + '/feedlookups/getallfeedlookups';
      return this.httpClient.get(url);
    }

    getPlaces() {
      const url = environment.BaseUrl + '/places/getallplaces';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.get(url, { headers: header });
    }

    getPlacesFeed() {
      const url = environment.BaseUrl + '/places/getallplacesfeed';
      return this.httpClient.get(url);
    }

    getAllSheepGroupByType(){
      const url = environment.BaseUrl + '/dashboard/getallsheepgroupbytype';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.get(url, { headers: header });
    }
  
    getAllLambsGroupByType(){
      const url = environment.BaseUrl + '/dashboard/getalllambsgroupbytype';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.get(url, { headers: header });
    }
  
    getAllAmountOfStoredFeed(){
      const url = environment.BaseUrl + '/dashboard/getallamountofstoredfeed';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.get(url, { headers: header });
    }

    getAllLambsPerMonth(){
      const url = environment.BaseUrl + '/dashboard/getalllambspermonth';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.get(url, { headers: header });
    }

    getAllSheepsPerAge (){
      const url = environment.BaseUrl + '/dashboard/getallsheepperage';
      const header = { 'Content-Type': 'application/json; charset=utf-8' };
      return this.httpClient.get(url, { headers: header });
    }

    getToken() {
      return localStorage.getItem('session-token');
    }

    checkExpiredToken() {
      const token = localStorage.getItem('session-token');
      if(token != null){
            this.decoded = jwt_decode(token);
            if (this.decoded.exp === undefined) {
                return false;
               }
             const date = new Date(0);
             const tokenExpDate = date.setUTCSeconds(this.decoded.exp);
             if (tokenExpDate.valueOf() > new Date().valueOf()) {
                  return true;
             } else {
                  return false;
              }
        }else{
          return false;
        }
  }


    logout() {

      //const url = environment.BaseUrl + '/logout';
      //const header = { 'Content-Type': 'application/json; charset=utf-8' };
      //this.httpClient.post(url, JSON.stringify("{}"), { headers: header }).subscribe(
      //     (responseData: any) => {
      //         localStorage.removeItem('session-token');
      //         this.router.navigate(['/sheepframe']);
      //     },
      //     (error: any) => {
      //         localStorage.removeItem('session-token');
      //         this.router.navigate(['/sheepframe']);
      //     }
      // );
      localStorage.removeItem('session-token');
      this.router.navigate(['/sheepframe']);
    }
}
