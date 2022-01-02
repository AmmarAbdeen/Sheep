import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { AppMainComponent } from './app.main.component';
import { AppNotfoundComponent } from './pages/app.notfound.component';
import { AppErrorComponent } from './pages/app.error.component';
import { AppAccessdeniedComponent } from './pages/app.accessdenied.component';
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
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';
import { DashboardComponent } from './dashboard/dashboard.component';
import { GeneralPaymentComponent } from './general-payment/general-payment.component';
import { IncomeComponent } from './income/income.component';
import { SalesComponent } from './sales/sales.component';
import { AddUserComponent } from './add-user/add-user.component';
import { SearchUserComponent } from './search-user/search-user.component';
import { LandingPageComponent } from './landing-page/landing-page.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    { path: 'home', component: AppMainComponent,
        children: [
            { path: 'dashboard', component: DashboardComponent },
            { path: 'sheepcomponents/addNewSheep', component: AddNewSheepComponent, canActivate: [AuthGuard]},
            { path: 'lambcomponents/addNewLamb', component: AddNewLambComponent, canActivate: [AuthGuard]},
            { path: 'lambcomponents/managelamb', component: LambManagementComponent, canActivate: [AuthGuard]},
            { path: 'sheepcomponents/managesheep', component: SheepManagementComponent, canActivate: [AuthGuard]},  
            { path: 'feedcomponents/savefeed', component: SaveFeedComponent, canActivate: [AuthGuard]},
            { path: 'feedcomponents/feedlookups', component: FeedLookupsComponent, canActivate: [AuthGuard]},
            { path: 'feedcomponents/storedfeed', component: StoredFeedComponent, canActivate: [AuthGuard]},
            { path: 'placecomponents/places', component: PlacesComponent, canActivate: [AuthGuard]},
            { path: 'placecomponents/placesfeed', component: PlacesFeedComponent, canActivate: [AuthGuard]},
            { path: 'medicinecomponents/medicine', component: MedicineComponent, canActivate: [AuthGuard]},
            { path: 'medicinecomponents/sheepmedicine', component:SheepDiseaseMedicineComponent, canActivate: [AuthGuard] },
            { path: 'sheepmovement/movement', component:SheepMovementComponent, canActivate: [AuthGuard] },
            { path: 'sheepmovement/movementsearch', component:SheepMovementSearchComponent , canActivate: [AuthGuard]},
            { path: 'payment/generalpayment', component:GeneralPaymentComponent , canActivate: [AuthGuard]},
            { path: 'payment/income', component:IncomeComponent , canActivate: [AuthGuard]},
            { path: 'payment/sales', component:SalesComponent , canActivate: [AuthGuard]},
            { path: 'user/adduser', component:AddUserComponent , canActivate: [AuthGuard]},
            { path: 'user/searchuser', component:SearchUserComponent , canActivate: [AuthGuard]}

        ]
    },
    {path: 'error', component: AppErrorComponent},
    {path: 'accessdenied', component: AppAccessdeniedComponent},
    {path: 'notfound', component: AppNotfoundComponent},
    {path: '**', redirectTo: '/notfound'},

];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'});
