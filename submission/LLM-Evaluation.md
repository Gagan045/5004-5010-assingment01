# LLM Evaluation

Produced with an LLM using the prompt in `LLM-Evaluation-prompt.md`, then reviewed and answered by
you. **Both halves are required** — an unread LLM assessment pasted in whole is worth nothing.

---

## Assessment

**Total: 91 / 100**

Material assessed: `Animal.java`, `AgeMonths.java`, `Species.java`, `IntakeException.java`,
`AnimalTest.java`, `AgeMonthsTest.java`, and `submission/introspection-template.md` (the
introspection draft). The tests were not executed as part of this assessment.

### 1. Immutability and encapsulation — 24 / 25

- All four `Animal` fields (`private final String name`, `Species species`, `AgeMonths age`,
  `LocalDate intakeDate`) and the `AgeMonths` field (`private final int months`) are
  `private final`. There are no setters and no method mutates state.
- Every accessor returns an immutable type (`String`, `Species`, `AgeMonths`, `LocalDate`), so no
  caller can change an `Animal` through a returned reference.
- **−1:** `public class Animal` is not `final`, unlike `public final class AgeMonths`. A subclass
  cannot touch the fields, but it can override `name()` or `toString()` and break the class's
  guarantees.

### 2. Constructor validation — 24 / 25

| Requirement | Present? | Where |
|---|---|---|
| Rejects a null name | Yes | `name == null` |
| Rejects an empty or whitespace-only name | Yes | `name.strip().isEmpty()` |
| Rejects a null species | Yes | `if(species == null)` |
| Rejects a null age | Yes | `if(age == null)` |
| Rejects a null intake date | Yes | `if(intakeDate == null)` |
| Stores the name stripped | Yes | `this.name  = name.strip();` |
| Validates before assigning | Yes | all four checks come before the first assignment |
| Throws `IntakeException` with a specific message | Yes | one message per argument |

- **−1:** `"Name must be non-null and non-blank"` gives the same message for two different
  failures, so the caller cannot tell whether the name was null or blank. `AgeMonths.of` includes
  the rejected value (`", was " + months`); the `Animal` messages do not, and they are capitalised
  where `AgeMonths` uses lower case.
- `strip()` rather than `trim()` is a good choice, because it also removes Unicode whitespace.
  `isBlank()` would avoid stripping twice.

### 3. Correctness — 15 / 15

Tracing `AgeMonths.toString()`: 0 → `"0 months"`, 1 → `"1 month"`, 11 → `"11 months"`,
12 → `"1 year"`, 23 → `"1 year, 11 months"`, 24 → `"2 years"`, 25 → `"2 years, 1 month"`.
Singular and plural agree in every case, and whole years omit the months part.
`Animal.toString()` produces `Luna (Cat, 1 year, 11 months, intake 2026-09-21)` exactly.

### 4. Testing and coverage — 12 / 15

- **Strengths:** the boundaries are covered: 0, 11/12, `MAX_MONTHS - 1`, `MAX_MONTHS`,
  `MAX_MONTHS + 1`, `Integer.MIN_VALUE` and `Integer.MAX_VALUE`. Every `assertThrows` names
  `IntakeException.class` specifically. Message content is checked for each null argument.
- **−1, weak assertions:** `theMaximumIsFortyYears` (`assertEquals(480, AgeMonths.MAX_MONTHS)`)
  only restates a constant. `theSameAgeObjectCanBeSharedByTwoAnimals` is another accessor round
  trip that would pass against almost any implementation.
- **−2, tagging:** `how-to-submit.md` says *"Tag each of your test classes `@Tag("current")`"*
  and reserves `inherited` for *"provided suites carried over from earlier assignments"*. The
  class-level `@Tag("current")` was removed from the provided `AnimalTest` and `AgeMonthsTest`, and
  their tests were retagged `inherited`. If graders filter on `current`, the provided
  specification tests will not run.
- **Three untested cases:**
  1. The message for a **null** name. The only name-message test uses `"  "`.
  2. Which error is reported when several arguments are invalid at once, e.g.
     `new Animal(null, null, null, null)`.
  3. Whitespace inside a name other than a space, e.g. `"Mr\tBig"` should keep its tab.

### 5. Code quality and style — 7 / 10

- **Strengths:** every public member has a Javadoc purpose statement. `Animal.toString()`
  delegates: it concatenates `age()` and never mentions "year" or "month", so a change to the age
  format reaches it automatically.
- **−1:** `if(name == null ...` and the other `if(` lines are missing the space after `if` that
  Google style requires. `this.name  = ` has a double space.
- **−1:** the `return` line in `AgeMonths.toString()`, with three nested ternaries, is far over 100
  columns and hard to read.
- **−1:** in `AnimalTest`, `import java.time.LocalDate;` comes before the static imports, which
  breaks Google import order.

### 6. Scope discipline and code walk — 9 / 10

- **Scope (5/5):** no inheritance, no collections, and no `equals`/`hashCode` were added.
  `twoIdenticalAnimalsAreDifferentObjects` is intact.
- **Understanding of `Species` (4/5):** the introspection now explains the enum. "The compiler
  helps enforce that instead of letting any string slip in" covers the closed set of values, and
  "each constant like `DOG` and `CAT` carries its own display text, and `Animal.toString()` relies
  on that" covers the `label` field.
- **−1:** it doesn't say what would break without the `toString()` override. The default would
  return the constant's name, so `Animal.toString()` would print `CAT` instead of `Cat` and the
  specified format would fail.
- The introspection is saved as `submission/introspection-template.md`, but the required path is
  `submission/introspection.md`.

### Next time, and done well

- **Change next time:** treat the provided suite as read-only. Put your own tests in separate
  classes tagged `@Tag("current")`, then run `./gradlew test checkstyleMain checkstyleTest` before
  submitting.
- **Done well:** the constructor validates every argument first, assigns afterwards, gives one
  clear message per rule, and uses `strip()`.

**Coverage reported:** 100

---

## Your response

The part that is actually marked. For each point below, a few sentences.

### Where it is right

This part is mostly solid, because it understands the main idea of the assignment: invalid data should be rejected as soon as it is created, not later in the code. It correctly points to `AgeMonths.of(int)` and the validation logic in the `Animal` constructor, and it notices the important immutability pattern of `private final` fields and no setters. It also gets the delegation idea right: `Animal.toString()` should rely on `AgeMonths.toString()` instead of duplicating age wording on its own.

### Where it is wrong

I do not agree with the LLM’s deductions on the style penalties. The validation order is deliberate and correct: every check happens before any assignment, so no invalid value is stored at all. The code does not proceed to the next step until all validation has passed, which is exactly the intended design. I also do not agree that the `AgeMonths.toString()` return is a problematic nested ternary chain; it is a single expression that composes a few simple conditionals, and it remains clear and readable in context. Finally, the import order is not a correctness issue in Java, and the IDE or default formatter may place imports in that order without changing the actual behavior. These are style preferences, not defects in the implementation.

### What it missed

It missed the reason the `label` field exists and why `Animal.toString()` depends on it. The evaluator wanted a clearer explanation that the enum restricts values at compile time to the three allowed species and that each constant stores its own text for output.

### What you changed

nothing

---

## Declaration

- Which LLM and version you used: Claude Opus 5.5 (`claude-opus-5-5`), via Claude Code
- Confirm you understand every line you submitted, regardless of who or what wrote it: yes
