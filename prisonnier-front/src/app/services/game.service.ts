import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

// Définir les types de données (tu peux les ajuster selon ton backend)
interface Game {
  id: number;
  playerName: string;
  rounds: number;
  status: string;
}

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private apiUrl = 'http://localhost/api/game'; // URL de ton backend

  constructor(private http: HttpClient) {}

  // Méthode pour créer un jeu
  createGame(playerName: string, rounds: number): Observable<any> {
    const params = new HttpParams()
      .set('playerName', playerName)
      .set('rounds', rounds.toString());
    return this.http.post<any>(`${this.apiUrl}/create`, {}, { params });
  }

  // Méthode pour rejoindre un jeu
  joinGame(gameId: number, playerName: string): Observable<any> {
    const params = new HttpParams()
      .set('gameId', gameId.toString())
      .set('playerName', playerName);
    return this.http.post<any>(`${this.apiUrl}/join`, {}, { params });
  }

  // Méthode pour jouer un tour
  playRound(gameId: number, playerId: number, move: string): Observable<string> {
    const params = new HttpParams()
      .set('gameId', gameId.toString())
      .set('playerId', playerId.toString())
      .set('move', move);
    return this.http.post<string>(`${this.apiUrl}/play`, {}, { params });
  }

  getStrategies() {
    return this.http.get<string[]>(`${this.apiUrl}/strategies`);
  }
  
  quitGame(playerId: number, strategy: string) {
    return this.http.post<{ message: string }>(`${this.apiUrl}/quitte`, null, {
      params: { playerId: playerId.toString(), strategie: strategy },
    });
  }

  getGameStatus(gameId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${gameId}/status`);
  }
}
