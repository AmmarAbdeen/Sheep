import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Lamb } from '../vo/Lamb';
import { Sheep } from '../vo/Sheep';

@Injectable({
  providedIn: 'root'
})
export class GeneralService {

  constructor(private httpClient: HttpClient) { }

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

savePlacesFeed(body: any) {
  const url = environment.BaseUrl + '/places/saveplacesfeed';
  const header = { 'Content-Type': 'application/json; charset=utf-8' };
  return this.httpClient.post(url, JSON.stringify(body), { headers: header });
}

getSheeps(body: any) {
  const url = environment.BaseUrl + '/getsheeps';
  const header = {'Content-Type': 'application/json; charset=utf-8'};
  return this.httpClient.post(url, JSON.stringify(body), {headers: header});
}

getSheepForEdit(body: any) {
  const url = environment.BaseUrl + '/getsheepbydata';
  const header = {'Content-Type': 'application/json; charset=utf-8'};
  return this.httpClient.post<Sheep>(url, JSON.stringify(body), {headers: header});
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
  return this.httpClient.get(url);
}

getPlacesFeed() {
  const url = environment.BaseUrl + '/places/getallplacesfeed';
  return this.httpClient.get(url);
}
}
