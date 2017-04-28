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
    // helyette viselkedést kell definiálni a hívásokra
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

  // Other tests

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

  // SINGLE: fires only one of the bays: in fireTorpedos_Single_Success() mock2 verify
  // SINGLE: For the first time the primary store is fired: in fireTorpedos_Single_Success() mock1 verify
  // Alternate firing: fire 3 torpedos and verify
  // test with 0 torpedo in both stores


}
