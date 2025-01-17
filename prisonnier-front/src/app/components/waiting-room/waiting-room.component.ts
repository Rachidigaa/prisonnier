import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GameService } from '../../services/game.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-waiting-room',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './waiting-room.component.html',
  styleUrls: ['./waiting-room.component.css'],
})
export class WaitingRoomComponent implements OnInit {
  gameId: number | null = null;
  playerId: number | null = null;
  isSecondPlayerJoined: boolean = false;

  showModal: boolean = false;
  countdown: number = 3;

  constructor(private gameService: GameService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.gameId = params['gameId'] ? +params['gameId'] : null;
      this.playerId = params['playerId'] ? +params['playerId'] : null;
      if (this.gameId && this.playerId) {
        this.checkForSecondPlayer();
      }
    });
  }

  checkForSecondPlayer() {
    const interval = setInterval(() => {
      if (this.gameId && this.playerId) {
        this.gameService.getGameStatus(this.gameId,this.playerId).subscribe(
          (game) => {
            if (game.players.length === 2) {
              this.isSecondPlayerJoined = true;
              clearInterval(interval);
              this.startCountdown();
            }
          },
          (error) => {
            console.error('Failed to fetch game status', error);
          }
        );
      }
    }, 2000);
  }

  startCountdown() {
    this.showModal = true;
    const countdownInterval = setInterval(() => {
      this.countdown--;
      if (this.countdown === 0) {
        clearInterval(countdownInterval);
        this.redirectToGame();
      }
    }, 1000);
  }

  redirectToGame() {
    this.router.navigate(['/game', this.gameId, this.playerId]);
  }
}
