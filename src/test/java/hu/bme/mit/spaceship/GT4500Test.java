package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private TorpedoStore mock1;
  private TorpedoStore mock2;

  private GT4500 ship;

  @Before
  public void init(){
    mock1 = mock(TorpedoStore.class);
    mock2 = mock(TorpedoStore.class);
    // TorpedoStore konstruktor paraméterét nem kell megadni,
    // helyette viselkedést kell definiálni a mockon töténő hívásokra
    this.ship = new GT4500(mock1, mock2);
  }

  @Test
  public void fireTorpedos_Single_Success(){
    // Arrange
    when(mock1.isEmpty()).thenReturn(false);
    when(mock1.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    // Verify
    verify(mock1, times(1)).isEmpty();
    verify(mock1, times(1)).fire(1);

    verify(mock2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedos_Alternate_Success(){
    // Arrange
    when(mock1.isEmpty()).thenReturn(false);
    when(mock2.isEmpty()).thenReturn(false);
    when(mock1.fire(1)).thenReturn(true);
    when(mock2.fire(1)).thenReturn(true);

    // Act and Verify: 1. fire
    ship.fireTorpedos(FiringMode.SINGLE);
    verify(mock1, times(1)).fire(1);
    verify(mock2, times(0)).fire(1);
    // Act and Verify: 2. fire
    ship.fireTorpedos(FiringMode.SINGLE);
    verify(mock1, times(1)).fire(1);
    verify(mock2, times(1)).fire(1);
    // Act and Verify: 3. fire
    ship.fireTorpedos(FiringMode.SINGLE);
    verify(mock1, times(2)).fire(1);
    verify(mock2, times(1)).fire(1);
    // Act and Verify: 4. fire
    ship.fireTorpedos(FiringMode.SINGLE);
    verify(mock1, times(2)).fire(1);
    verify(mock2, times(2)).fire(1);
  }

  @Test
  public void fireTorpedos_With_Empty_TorpedoStores_Fail(){
    when(mock1.isEmpty()).thenReturn(true);
    when(mock2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);

    // Verify
    verify(mock1, times(0)).fire(1);
    verify(mock2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedos_Fire_Another_If_Store_Is_Empty_Success(){
    when(mock1.isEmpty()).thenReturn(true);
    when(mock2.isEmpty()).thenReturn(false);
    when(mock2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    // Verify
    verify(mock1, times(0)).fire(1);
    verify(mock2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedos_On_Failure_Stop_Firing_And_Fail() {
    // Arrange
    when(mock1.isEmpty()).thenReturn(false);
    when(mock2.isEmpty()).thenReturn(false);
    when(mock1.fire(1)).thenReturn(false);
    when(mock2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);

    // Verify
    verify(mock1, times(1)).fire(1);
    verify(mock2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedos_Try_Fire_Primary_Again_Success(){
    // Arrange
    when(mock1.isEmpty()).thenReturn(false);
    when(mock2.isEmpty()).thenReturn(true);
    when(mock1.fire(1)).thenReturn(true);
    when(mock2.fire(1)).thenReturn(false);

    // Act
    ship.fireTorpedos(FiringMode.SINGLE);
    boolean result = ship.fireTorpedos(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    // Verify
    verify(mock1, times(2)).fire(1);
    verify(mock2, times(0)).fire(1);
  }

  // FiringMode.ALL tests

  @Test
  public void fireTorpedos_All_Success(){
    // Arrange
    when(mock1.isEmpty()).thenReturn(false);
    when(mock2.isEmpty()).thenReturn(false);
    when(mock1.fire(1)).thenReturn(true);
    when(mock2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    // Verify
    verify(mock1, times(1)).isEmpty();
    verify(mock1, times(1)).fire(1);
    verify(mock2, times(1)).isEmpty();
    verify(mock2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedos_All_Empty_Stores_Fail(){
    // Arrange
    when(mock1.isEmpty()).thenReturn(true);
    when(mock2.isEmpty()).thenReturn(true);
    when(mock1.fire(1)).thenReturn(false);
    when(mock2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(false, result);

    // Verify
    verify(mock1, times(1)).isEmpty();
    verify(mock2, times(1)).isEmpty();

    verify(mock1, times(0)).fire(1);
    verify(mock2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedos_Both_Failure_Fail(){
    // Arrange
    when(mock1.isEmpty()).thenReturn(false);
    when(mock2.isEmpty()).thenReturn(false);
    when(mock1.fire(1)).thenReturn(false);
    when(mock2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(false, result);

    // Verify
    verify(mock1, times(1)).isEmpty();
    verify(mock2, times(1)).isEmpty();

    verify(mock1, times(1)).fire(1);
    verify(mock2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedos_One_Store_Fired_Only_Success(){
    // Arrange
    when(mock1.isEmpty()).thenReturn(true);
    when(mock2.isEmpty()).thenReturn(false);
    when(mock1.fire(1)).thenReturn(false);
    when(mock2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedos(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    // Verify
    verify(mock1, times(1)).isEmpty();
    verify(mock2, times(1)).isEmpty();

    verify(mock1, times(0)).fire(1);
    verify(mock2, times(1)).fire(1);
  }

  // JUnit Java 8 testing with Lambda expressions
  @Test(expected = UnsupportedOperationException.class)
  public void fireLasers_Not_Yet_Implemented() {
    ship.fireLasers(FiringMode.SINGLE);
  }

  // Test ideas from method specification

  /**
  * Tries to fire the torpedo stores of the ship.
  *
  * @param firingMode how many torpedo bays to fire
  * 	SINGLE: fires only one of the bays.
  * 			- For the first time the primary store is fired.
  * 			- To give some cooling time to the torpedo stores, torpedo stores are fired alternating.
  * 			- But if the store next in line is empty the ship tries to fire the other store.
  * 			- If the fired store reports a failure, the ship does not try to fire the other one.
  * 	ALL:	tries to fire both of the torpedo stores.
  *
  * @return whether at least one torpedo was fired successfully
  */

  // done: SINGLE: fires only one of the bays: in fireTorpedos_Single_Success() mock2 verify
  // done: SINGLE: For the first time the primary store is fired: in fireTorpedos_Single_Success() mock1 verify
  // done: Alternate firing - fire 4 torpedos and verify: in fireTorpedos_Alternate_Success()
  // done: With 0 torpedo in both stores: in fireTorpedos_With_Empty_TorpedoStores_Fail()
  // done: In case of Empty store, fire another: in fireTorpedos_Fire_Another_If_Store_Is_Empty_Success()
  // done: SINGLE: on fire failure do not fire the other one: in fireTorpedos_On_Failure_Stop_Firing_And_Fail()
  // done: SINGLE: if we fired the primary, and the secondary is empty, we try to fire the primary instead: in fireTorpedos_Try_Fire_Primary_Again_Success()
  // done: ALL: check if at least 1 torpedo was fired (as a return of true boolean): in fireTorpedos_All_Success()
    // done: fails if the Stores are empty: in fireTorpedos_All_Empty_Stores_Fail()
    // done: fails if both Stores fire reports failure: in fireTorpedos_Both_Failure_Fail()
    // success if at least one of the stores can fire: in fireTorpedos_One_Store_Fired_Only_Success()

  // done: fireLasers throws not yet implemented
}
