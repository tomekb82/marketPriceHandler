# marketPriceHandler
### Project contains of following packages:
- domain: contains domain logic, keep invariants, implement logic for adjusting price (commission) and define in/out bound interfaces
- application: contains controller and application service using repositories and domain object
- infrastructure: contains adapters for message converters, readers (subscriber), repository and spring configuration

+ unit tests: verifying higher order functions, to be able to refactor internal implementation

### TODOs:
- implement persistence layer and adding @Transactional - currently simple hash maps has been used
- security added on api level
- integration test for ctrl could be added (if needed)
- changing throwing exception to returning objects with failure (using vavr monads like Either)
- define contract with customers using our API (spring contract for handling model changes)
- add more test scenarios for some corner cases

