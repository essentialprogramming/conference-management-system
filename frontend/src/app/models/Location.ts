export class Location{
  id: string;
  country: string;
  city: string;
  weather: string;


  constructor(id: string, country: string, city: string, weather: string) {
    this.id = id;
    this.country = country;
    this.city = city;
    this.weather = weather;
  }
}
