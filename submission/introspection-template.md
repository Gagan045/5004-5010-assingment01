# Design Introspection

Your own reflection on the design decisions you made this week. Written in your own words — this is
distinct from the LLM's assessment of your code, and distinct from your code-walk video.

Aim for a page. Cite your actual code: name the class and method you are talking about.

## 1. What you built

This week I built the shelter intake model with `Animal`, `AgeMonths`, `Species`, and `IntakeException`. `Animal` stores one animal record, `AgeMonths` handles age validation, and `IntakeException` throws when bad input is given. I chose `Species` as an enum because the shelter only allows a fixed set of animal kinds, and the compiler helps enforce that instead of letting any string slip in; each constant like `DOG` and `CAT` carries its own display text, and `Animal.toString()` relies on that when it prints labels like "Cat".

## 2. Design decisions

The main choice was making bad states impossible to create instead of checking later. I used `AgeMonths.of(int)` and validation in the `Animal` constructor so invalid data gets rejected right away, which keeps the rest of the code simpler and safer. I also picked an enum for `Species` instead of a few loose strings because the valid options are fixed and finite, and the `label` field plus `label()` and `toString()` methods let each constant present itself in a clean, human-readable way.

I also decided that `AgeMonths` should own the age formatting instead of `Animal`. That way `Animal.toString()` just combines pieces, and the age text stays consistent without duplicating logic in two places.

## 3. Invariants

A valid `AgeMonths` is always non-negative and never above `MAX_MONTHS`, and that is enforced in `AgeMonths.of(int)`. Once an object exists, it cannot be changed, so the rule stays true for the whole lifetime of the object.

A valid `Animal` always has a non-blank name, a real species, a valid age, and a non-null intake date. Those checks happen in the constructor before the fields are stored, so the object is never created in a broken state.

## 4. Testing

I added checks for the edge values and for the actual exception messages, because a test that only checks the exception type is not very useful. I wanted to make sure the app was failing for the right reason and not just failing at all.

The hardest test was the one for `AgeMonths.toString()`, because the format is very specific and small mistakes like singular vs plural matter. It taught me that writing code is not the hard part; reading the exact rule and matching it carefully is the real challenge.

## 5. What I would change

If I had more time, I would make the validation a little cleaner by pulling repeated checks into helper methods instead of repeating the same pattern. I would also add more tests for boundary cases and exact error text so the behavior is clearer to future readers.

The thing that stopped me from doing more was the assignment’s focus: it wanted a small, clear design, not extra features. I kept the code simple so the core idea stayed strong.

## 6. What I found hard

The hardest part was thinking in terms of guarantees instead of just writing methods. Once I realized the right place to validate is at creation time, the design got much easier and the code felt more solid.

The other struggle was `AgeMonths.toString()`, because the wording had to match the test exactly. Once I treated the spec as a contract and matched the edge cases carefully, it finally clicked.
