import { Component } from '@angular/core';
import { GameService } from '../../services/game.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-join-game',
  standalone: true,
  imports: [FormsModule, ButtonModule, CommonModule],
  templateUrl: './join-game.component.html',
  styleUrl: './join-game.component.css'
})
export class JoinGameComponent {
  gameId: number | null = null;
  playerName: string = '';
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(private gameService: GameService, private router: Router) {}
  joinGame() {
    if (this.gameId && this.playerName.trim()) {
      this.errorMessage = null;
      this.gameService
        .joinGame(this.gameId, this.playerName.trim())
        .subscribe(
          (response) => {
            this.router.navigate(['/waiting-room'], {
              queryParams: { gameId: response.game.id, playerId: response.playerId },
            });
          },
          (error) => {
            this.errorMessage = 'Failed to join game. Please check the details.';
          }
        );
    } else {
      this.errorMessage = 'Please enter a valid player name.';
    }
  }
}
