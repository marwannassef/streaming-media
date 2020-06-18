export class User {

    id: number;
    fullName: String;
    email: String;
    password: String;
    passwordVarify: String;
    birthDate: String;
    banned: boolean;

    constructor(
        fullName: String,
        email: String,
        password: String,
        birthDate: String
    ) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
    }

}
