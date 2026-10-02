package edu.northeastern.shelter;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The specification for {@link Animal}, written as tests.
 *
 * <p>Note {@link #twoIdenticalAnimalsAreDifferentObjects()} at the bottom: it asserts the
 * <em>absence</em> of value equality. That is not an oversight to be fixed — it is this lab's
 * boundary, and a later lab moves it.
 */
class AnimalTest {

  private static final LocalDate INTAKE = LocalDate.of(2026, 9, 21);

  private static Animal luna() {
    return new Animal("Luna", Species.CAT, AgeMonths.of(23), INTAKE);
  }

  @Test
  @Tag("inherited")
  void accessorsReturnWhatWasPassedIn() {
    Animal luna = luna();
    assertEquals("Luna", luna.name());
    assertEquals(Species.CAT, luna.species());
    assertEquals(23, luna.age().months());
    assertEquals(INTAKE, luna.intakeDate());
  }

  @Test
  @Tag("inherited")
  void theNameIsTrimmed() {
    assertEquals("Luna", new Animal("  Luna  ", Species.CAT, AgeMonths.of(1), INTAKE).name());
  }

  @Test
  @Tag("inherited")
  void anInteriorSpaceIsNotWhitespaceToBeStripped() {
    assertEquals(
        "Mr Bigglesworth",
        new Animal(" Mr Bigglesworth ", Species.CAT, AgeMonths.of(1), INTAKE).name());
  }

  @Test
  @Tag("inherited")
  void aNullNameIsRefused() {
    assertThrows(
        IntakeException.class, () -> new Animal(null, Species.DOG, AgeMonths.of(1), INTAKE));
  }

  @Test
  @Tag("inherited")
  void anEmptyNameIsRefused() {
    assertThrows(IntakeException.class, () -> new Animal("", Species.DOG, AgeMonths.of(1), INTAKE));
  }

  @Test
  @Tag("inherited")
  void aNameOfOnlyWhitespaceIsRefused() {
    assertThrows(
        IntakeException.class, () -> new Animal("   ", Species.DOG, AgeMonths.of(1), INTAKE));
  }

  @Test
  @Tag("inherited")
  void aNullSpeciesIsRefused() {
    assertThrows(IntakeException.class, () -> new Animal("Rex", null, AgeMonths.of(1), INTAKE));
  }

  @Test
  @Tag("inherited")
  void aNullAgeIsRefused() {
    assertThrows(IntakeException.class, () -> new Animal("Rex", Species.DOG, null, INTAKE));
  }

  @Test
  @Tag("inherited")
  void aNullIntakeDateIsRefused() {
    assertThrows(
        IntakeException.class, () -> new Animal("Rex", Species.DOG, AgeMonths.of(1), null));
  }

  @Test
  @Tag("inherited")
  void toStringFollowsTheSpecifiedFormat() {
    assertEquals("Luna (Cat, 1 year, 11 months, intake 2026-09-21)", luna().toString());
  }

  @Test
  @Tag("inherited")
  void toStringDelegatesToTheAgeDescription() {
    Animal pup = new Animal("Pip", Species.DOG, AgeMonths.of(1), INTAKE);
    assertEquals("Pip (Dog, 1 month, intake 2026-09-21)", pup.toString());
  }

  @Test
  @Tag("inherited")
  void theIntakeDateSurvivesTheRoundTrip() {
    Animal luna = luna();
    assertSame(INTAKE, luna.intakeDate());
  }

  @Test
  @Tag("inherited")
  void twoIdenticalAnimalsAreDifferentObjects() {
    // Deliberate: Animal has no value equality in this lab, so the default identity comparison
    // applies. A later lab gives the Animal family a proper equals/hashCode, and this test moves.
    assertNotSame(luna(), luna());
    assertEquals(false, luna().equals(luna()));
  }

  @Test
  @Tag("inherited")
  void theRefusalSaysWhichArgumentWasWrong() {
    // The rubric grades messages that name the problem. "invalid" tells a reader nothing.
    IntakeException blankName =
        assertThrows(
            IntakeException.class, () -> new Animal("  ", Species.DOG, AgeMonths.of(1), INTAKE));
    assertTrue(
        blankName.getMessage().toLowerCase().contains("name"),
        "the message should name the offending argument");
  }


  @Tag("current")
  @Test
  void tabsAndNewlinesAroundTheNameAreTrimmed() {
    assertEquals("Luna", new Animal("\tLuna\n", Species.CAT, AgeMonths.of(1), INTAKE).name());
  }


  @Tag("current")
  @Test
  void aSingleCharacterNameIsAccepted() {
    assertEquals("X", new Animal("X", Species.DOG, AgeMonths.of(1), INTAKE).name());
  }

  @Test
  @Tag("current")
  void aNameOfOnlyTabsAndNewlinesIsRefused() {
    assertThrows(
        IntakeException.class, () -> new Animal("\t\n", Species.DOG, AgeMonths.of(1), INTAKE));
  }

  @Test
  @Tag("current")
  void aNameWithUnicodeWhitespaceIsStripped() {
    // U+2003 (em space) is whitespace to isBlank() and strip(), but not to trim().
    assertEquals("Luna", new Animal(" Luna ", Species.CAT, AgeMonths.of(1), INTAKE).name());
  }

  @Test
  @Tag("current")
  void aNullSpeciesMessageMentionsSpecies() {
    IntakeException e =
        assertThrows(IntakeException.class, () -> new Animal("Rex", null, AgeMonths.of(1), INTAKE));
    assertTrue(
        e.getMessage().toLowerCase().contains("species"),
        "the message should name the offending argument");
  }

  @Test
  @Tag("current")
  void aNullAgeMessageMentionsAge() {
    IntakeException e =
        assertThrows(IntakeException.class, () -> new Animal("Rex", Species.DOG, null, INTAKE));
    assertTrue(
        e.getMessage().toLowerCase().contains("age"),
        "the message should name the offending argument");
  }

  @Test
  @Tag("current")
  void aNullIntakeDateMessageMentionsTheDate() {
    IntakeException e =
        assertThrows(
            IntakeException.class, () -> new Animal("Rex", Species.DOG, AgeMonths.of(1), null));
    assertTrue(
        e.getMessage().toLowerCase().contains("date"),
        "the message should name the offending argument");
  }

  @Test
  @Tag("current")
  void toStringForABird() {
    Animal bird = new Animal("Tweety", Species.BIRD, AgeMonths.of(5), INTAKE);
    assertEquals("Tweety (Bird, 5 months, intake 2026-09-21)", bird.toString());
  }

  @Test
  @Tag("current")
  void toStringUsesTheTrimmedName() {
    Animal luna = new Animal("  Luna  ", Species.CAT, AgeMonths.of(23), INTAKE);
    assertEquals("Luna (Cat, 1 year, 11 months, intake 2026-09-21)", luna.toString());
  }

  @Test
  @Tag("current")
  void theSameAgeObjectCanBeSharedByTwoAnimals() {
    AgeMonths age = AgeMonths.of(6);
    Animal a = new Animal("Ace", Species.DOG, age, INTAKE);
    Animal b = new Animal("Bea", Species.CAT, age, INTAKE);
    assertSame(age, a.age());
    assertSame(age, b.age());
  }

  @Test
  @Tag("current")
  void aFutureIntakeDateIsAccepted() {
    // The spec has no rule against future dates; this pins down that none was invented.
    LocalDate future = LocalDate.of(2099, 1, 1);
    assertEquals(future, new Animal("Rex", Species.DOG, AgeMonths.of(1), future).intakeDate());
  }
}
