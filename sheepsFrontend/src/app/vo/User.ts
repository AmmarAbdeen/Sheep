import { Privilege } from "./Privilege";

export class User {
    id: number;
    email: string;
    fullName: string;
    mobileNumber: string;
    nationalId: string;
    password: string;
    userName: string;
    privileges:Privilege[];
}