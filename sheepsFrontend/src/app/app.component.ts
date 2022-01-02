import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
declare var gtag;  
@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
})
export class AppComponent {

    constructor(router: Router) {
        const navEndEvent$ = router.events.pipe(
          filter(e => e instanceof NavigationEnd)
        );
        navEndEvent$.subscribe((e: NavigationEnd) => {
          gtag('config', 'G-D6CYCSJE0T', {'page_path':e.urlAfterRedirects});
        });

}
}
