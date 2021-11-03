import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { AppMainComponent } from './app.main.component';
import { AppNotfoundComponent } from './pages/app.notfound.component';
import { AppErrorComponent } from './pages/app.error.component';
import { AppAccessdeniedComponent } from './pages/app.accessdenied.component';
import { DashboardComponent } from './pages/dashboard.component';
import { AddNewSheepComponent } from './add-new-sheep/add-new-sheep.component';
import { SheepManagementComponent } from './sheep-management/sheep-management.component';
import { AddNewLambComponent } from './add-new-lamb/add-new-lamb.component';
import { LambManagementComponent } from './lamb-management/lamb-management.component';
import { SaveFeedComponent } from './save-feed/save-feed.component';
import { FeedLookupsComponent } from './feed-lookups/feed-lookups.component';
import { StoredFeedComponent } from './stored-feed/stored-feed.component';
import { PlacesComponent } from './places/places.component';
import { PlacesFeedComponent } from './places-feed/places-feed.component';
import { MedicineComponent } from './medicine/medicine.component';
import { MedicineDiseaseOfSheep } from './vo/MedicineDiseaseOfSheep';
import { SheepDiseaseMedicineComponent } from './sheep-disease-medicine/sheep-disease-medicine.component';
import { SheepMovementComponent } from './sheep-movement/sheep-movement.component';
import { SheepMovementSearchComponent } from './sheep-movement-search/sheep-movement-search.component';

export const routes: Routes = [
    { path: 'sheepframe', component: AppMainComponent,
        children: [
            { path: 'dashboard', component: DashboardComponent },
            { path: 'sheepcomponents/addNewSheep', component: AddNewSheepComponent},
            { path: 'lambcomponents/addNewLamb', component: AddNewLambComponent},
            { path: 'lambcomponents/managelamb', component: LambManagementComponent},
            { path: 'sheepcomponents/managesheep', component: SheepManagementComponent},  
            { path: 'feedcomponents/savefeed', component: SaveFeedComponent},
            { path: 'feedcomponents/feedlookups', component: FeedLookupsComponent},
            { path: 'feedcomponents/storedfeed', component: StoredFeedComponent},
            { path: 'placecomponents/places', component: PlacesComponent},
            { path: 'placecomponents/placesfeed', component: PlacesFeedComponent},
            { path: 'medicinecomponents/medicine', component: MedicineComponent},
            { path: 'medicinecomponents/sheepmedicine', component:SheepDiseaseMedicineComponent },
            { path: 'sheepmovement/movement', component:SheepMovementComponent },
            { path: 'sheepmovement/movementsearch', component:SheepMovementSearchComponent }
        ]
    },
    {path: 'error', component: AppErrorComponent},
    {path: 'accessdenied', component: AppAccessdeniedComponent},
    {path: 'notfound', component: AppNotfoundComponent},
    {path: '**', redirectTo: '/notfound'},

];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'});
