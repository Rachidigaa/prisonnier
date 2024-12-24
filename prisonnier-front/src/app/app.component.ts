import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { GameService } from './services/game.service';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, HeaderComponent, HomeComponent,ButtonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'persionner-front';

  response: any;

  constructor(private gameService: GameService) {}

  ngOnInit(): void {}

  testCreateGame() {
    this.gameService.createGame('Player1', 5).subscribe((res) => {
      this.response = res;
    });
  }
}
