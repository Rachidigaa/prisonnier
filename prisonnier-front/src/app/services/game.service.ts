import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

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
  private apiUrl = '/api/game'; // URL of the backend

  constructor(private http: HttpClient) {}

  // Method to create a game
  createGame(playerName: string, rounds: number): Observable<any> {
    const params = new HttpParams()
      .set('playerName', playerName)
      .set('rounds', rounds.toString());
    return this.http.post<any>(`${this.apiUrl}/create`, {}, { params }).pipe(
      tap((response) => console.log('Create Game Response:', response))
    );
  }

  // Method to join a game
  joinGame(gameId: number, playerName: string): Observable<any> {
    const params = new HttpParams()
      .set('gameId', gameId.toString())
      .set('playerName', playerName);
    return this.http.post<any>(`${this.apiUrl}/join`, {}, { params }).pipe(
      tap((response) => console.log('Join Game Response:', response))
    );
  }

  // Method to play a round
  playRound(gameId: number, playerId: number, move: string): Observable<string> {
    const params = new HttpParams()
      .set('gameId', gameId.toString())
      .set('playerId', playerId.toString())
      .set('move', move);
    return this.http.post<string>(`${this.apiUrl}/play`, {}, { params }).pipe(
      tap((response) => console.log('Play Round Response:', response))
    );
  }

  // Method to get available strategies
  getStrategies(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/strategies`).pipe(
      tap((response) => console.log('Get Strategies Response:', response))
    );
  }

  // Method to quit a game
  quitGame(playerId: number, strategy: string): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(
      `${this.apiUrl}/quitte`,
      null,
      {
        params: { playerId: playerId.toString(), strategie: strategy },
      }
    ).pipe(
      tap((response) => console.log('Quit Game Response:', response))
    );
  }

  // Method to get the game status
  getGameStatus(gameId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${gameId}/status`).pipe(
      tap((response) => console.log('Get Game Status Response:', response))
    );
  }
}
