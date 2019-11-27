export const common = {
    isNotBlank: (str) => {
      if (str !== '' && str !== null && str !== undefined) {
        return true
      }
    
      return false
    }
  }