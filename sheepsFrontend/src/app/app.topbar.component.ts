import {Component} from '@angular/core';
import {AppMainComponent} from './app.main.component';
import { GeneralService } from './service/general.service';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopBarComponent {

    user: any;

    constructor(public app: AppMainComponent,public generalService: GeneralService) {}

    ngOnInit() {

        this.user = JSON.parse(localStorage.getItem('user'));
    }

}
